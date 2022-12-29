<?php
if (isset($_POST['x']) && isset($_POST['y']) && isset($_POST['r'])) {

    $x = $_POST['x'];
    $y = $_POST['y'];
    $r = $_POST['r'];
    $success = 0;
    $cur_time = null;
    $ex_time = null;
    $res = null;

    if (validate($x, $y, $r)) {
        if (xIsValid($x) && yIsValid($y) && rIsValid($r)) {
            $cur_time = date('H:i:s', time() - $_POST['date'] * 60);
            $ex_time = round(microtime(true) - $_SERVER['REQUEST_TIME_FLOAT'], 7);
            $res = isIn($x, $y, $r);
            $success = 1;
        }
        echo result($success,$x,$y,$r,$cur_time,$ex_time,$res);
    }
}

function isIn($x, $y, $r)
{
    return (isCircle($x, $y, $r) || isRect($x, $y, $r) || isTriangle($x, $y, $r));
}

function isTriangle($x, $y, $r)
{
    return ($x <= 0 && $y >= 0 && ($y - $x / 2 + $r / 2) <= 0);
}

function isCircle($x, $y, $r)
{
    return ($x >= 0 && $y >= 0 && $x * $x + $y * $y <= ($r / 2) * ($r / 2));
}

function isRect($x, $y, $r)
{
    return ($x <= 0 && $y <= 0 && $x >= -1*$r && $y >= -1*$r/2);
}

function result($success, $x, $y, $r, $cur_time, $ex_time, $res)
{

    $str_res = ($res) ? "true" : "false";

    return '{"success":'. $success . ',"x":' . $x . ',"y":' . $y . ',"r":' . $r . ',"current":"' . $cur_time . '","execution":"' . $ex_time . '","result":"' . $str_res . '"}';

}

function validate($x, $y, $r)
{
    return is_numeric($x) && is_numeric($y) && is_numeric($r);
}
function xIsValid($x)
{
    /** Выполняет валидацию поля X.
     * x ∈ { -5, -4, -3, -2, -1, 0, 1, 2, 3 }
     **/
    return ($x == "-5" || $x == "-4" || $x == "-3" || $x == "-2" || $x == "-1" || $x == "0" || $x == "1" || $x == "2" || $x == "3");
}
function yIsValid($y)
{
    /** Выполняет валидацию поля Y.
     * y ∈ { -5, ..., 5}
     **/
    
    $reg1 = "/^-?[0-4](\.\d+)?$/";
    $reg2 = "/^-?5(\.0+)?$/";
    
    return (preg_match($reg1, $y)
        || preg_match($reg2, $y));   
}

function rIsValid($r)
{
    /** Выполняет валидацию поля R.
     * x ∈ { 1, 1.5, 2, 2.5, 3}
     **/
    return ($r == "1" || $r == "1.5" || $r == "2" || $r == "2.5" || $r == "3");
}
?>