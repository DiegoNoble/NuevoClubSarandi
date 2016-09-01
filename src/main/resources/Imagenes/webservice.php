
<?php

require("config.php");

if ($_POST["accion"] == "cobro") {



    $sql = 'UPDATE recibos SET
                    medioPagoId   = ' . $_POST["id_medio_pago"] . ',
                    medioPago     = "' . $_POST["medio_pago"] . '",
                    fechaHoraPago = "' . $_POST["fecha_hora_pago"] . '",
                    paga          = 1
                WHERE idSecreto = "' . $_POST["id_secreto"] . '"'
    ;
    mysql_query($sql) or die(mysql_error());

    echo "1";
} elseif ($_POST["accion"] == "consulta") {

    $sql = "SELECT * FROM recibos WHERE paga=1";

    $result = mysql_query($sql);

    if ($result != NULL) {

        $xmlDom = new DOMDocument();
        $xmlDom->appendChild($xmlDom->createElement('resultados'));
        $xmlRoot = $xmlDom->documentElement;


        while ($row = mysql_fetch_array($result)) {


            $xmlRowElementNode = $xmlDom->createElement('respuesta');

            $i = 0;
            for ($i = 0; $i < mysql_num_fields($result); $i++) {
                $xmlRowElement = $xmlDom->createElement(mysql_field_name($result, $i));
                $xmlText = $xmlDom->createTextNode($row[$i]);
                $xmlRowElement->appendChild($xmlText);

                $xmlRowElementNode->appendChild($xmlRowElement);
            }

            $xmlRoot->appendChild($xmlRowElementNode);
        }

        /* echo $xmlDom->saveXML();
          //here you can work with the results...
          header("Content-type:text/xml");
          echo "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
          echo "<transaccion>" . $row['transaccion'] . "</transaccion>";
          echo "<nombre>" . $row['nombre'] . "</nombre>";
          echo "<apellido>" . $row['apellido'] . "</apellido>";
          echo "<email>" . $row['email'] . "</email>";
          echo "<concepto>" . $row['concepto'] . "</concepto>";
          echo "<moneda>" . $row['moneda'] . "</moneda>";
          echo "<monto>" . $row['monto'] . "</monto>";
          echo "<fecha>" . $row['fecha'] . "</fecha>";
          echo "<medioPagoId>" . $row['medioPagoId'] . "</medioPagoId>";
          echo "<medioPago>" . $row['medioPago'] . "</medioPago>";
          echo "<nroTalon>" . $row['nroTalon'] . "</nroTalon>";
          echo "<idSecreto>" . $row['idSecreto'] . "</idSecreto>";
          echo "<urlPDF>" . $row['urlPDF'] . "</urlPDF>";
          echo "<fechaHoraPago>" . $row['fechaHoraPago'] . "</fechaHoraPago>";
          echo "<paga>" . $row['paga'] . "</paga>"; */

        //header('Content-type:  text/xml');
        echo $xmlDom->saveXML();
        mysql_free_result($result);
    } else {
        echo "sql sin resultados";
        mysql_close();
    }
} else {
    echo "0";
}
?>
