<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");

include("conexion.php");

$input = json_decode(file_get_contents('php://input'), true);

if (!isset($input['usuario']) || !isset($input['clave'])) {
    http_response_code(400);
    echo json_encode(["error" => "Faltan credenciales"]);
    exit;
}

$usuario = trim($input['usuario']);
$clave = trim($input['clave']);

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8mb4", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

	//Query para Buscar Usuario y Clave
	$sql="SELECT user_id, user_nombre,user_apellido,user_correoe,user_estado from tbl_user where user_usuario=? AND user_clave=? and user_estado='ACTIVO'";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$usuario, $clave]);

    if ($stmt->rowCount() === 0) 
	{
        //http_response_code(401);
        echo json_encode(["success" => false,"error" => "Usuario o clave incorrectos"]);
        exit;
    }

    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($user['user_estado'] !== 'ACTIVO') 
	{
        //http_response_code(403);
        echo json_encode(["success" => false,"error" => "Cuenta desactivada"]);
        exit;
    }
    echo json_encode
	([
        "success" => true,
        "message" => "Inicio de sesión exitoso",
        "data"=>$user
    ]);
} 
catch (PDOException $e) 
{
    //http_response_code(500);
    echo json_encode([
	"success"=>false,
	"message" => "Error interno del servidor"]);
}
?>