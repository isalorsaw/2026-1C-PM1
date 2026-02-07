<?php
include("conexion.php");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *"); // Permitir CORS para pruebas

try 
{
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo json_encode(["status" => "online", "message" => "Conexión exitosa"]);
} catch (PDOException $e) 
{
    http_response_code(503);
    echo json_encode([
        "status" => "offline",
        "message" => "Error: " . $e->getMessage()
    ]);
}
?>