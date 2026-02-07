<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *"); // Solo en desarrollo; en producción, restringe a tu dominio
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");

// Solo permitir método POST
if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    echo json_encode(["error" => "Método no permitido. Usa POST."]);
    exit;
}

$input = json_decode(file_get_contents('php://input'), true);

if (!isset($input['nombre']) || !isset($input['apellido'])) {
    http_response_code(400);
    echo json_encode(["error" => "Faltan campos: nombre o email"]);
    exit;
}

$id=0;
$dni = trim($input['dni']);
$nombre = trim($input['nombre']);
$apellido = trim($input['apellido']);
$correoe = filter_var(trim($input['correoe']), FILTER_SANITIZE_EMAIL);
$usuario = trim($input['usuario']);
$clave = trim($input['clave']);
$estado = trim($input['estado']);
$foto="";

if (!filter_var($correoe, FILTER_VALIDATE_EMAIL)) {
    http_response_code(400);
    echo json_encode(["error" => "Email inválido"]);
    exit;
}

include("conexion.php");

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8mb4", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $stmt = $pdo->prepare("INSERT INTO tbl_user VALUES (?,?,?,?,?,?,?,?,?)");
    $stmt->execute([$id,$dni,$nombre,$apellido,$correoe,$usuario,$clave,$foto,$estado]);

    echo json_encode([
        "success" => true,
        "message" => "Usuario creado",
        "id" => $pdo->lastInsertId()
    ]);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(["error" => "Error al guardar en la base de datos: " . $e->getMessage()]);
}
?>