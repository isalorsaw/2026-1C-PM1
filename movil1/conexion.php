<?php
header("Content-Type: application/json; charset=UTF-8");

$host = "localhost";
$dbname = "movil2";
$username = "root";
$password = "root";

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $stmt = $pdo->query("select * from tbl_user limit 1");
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

    //echo json_encode($result);
} catch (PDOException $e) {
    http_response_code(500);
    //echo json_encode(["error" => "Error en la base de datos: " . $e->getMessage()]);
}
?>