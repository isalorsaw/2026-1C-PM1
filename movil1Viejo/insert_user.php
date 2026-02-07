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

if (!isset($input['nombre']) || !isset($input['email'])) {
    http_response_code(400);
    echo json_encode(["error" => "Faltan campos: nombre o email"]);
    exit;
}

$nombre = trim($input['nombre']);
$email = filter_var(trim($input['email']), FILTER_SANITIZE_EMAIL);

if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    http_response_code(400);
    echo json_encode(["error" => "Email inválido"]);
    exit;
}

// Configuración de la base de datos
$host = "localhost";
$dbname = "tu_base_de_datos";
$username = "tu_usuario";
$password = "tu_contraseña";

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8mb4", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $stmt = $pdo->prepare("INSERT INTO usuarios (nombre, email) VALUES (?, ?)");
    $stmt->execute([$nombre, $email]);

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