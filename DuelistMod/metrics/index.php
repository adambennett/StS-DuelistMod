This page is the destination for metric uploads.

<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $json = file_get_contents('php://input');

    $directory = 'run/';

    if (!is_dir($directory)) {
        mkdir($directory, 0751, true);
    }

    $file_name = $directory . time();
    $file = fopen($file_name, "w");
    fwrite($file, $json);
    fclose($file);
    chmod($file_name, 0640);
}
?>
