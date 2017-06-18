<?php
    header ("Content-type: image/png");
    $w = intval($_GET["w"]);
    $h = intval($_GET["h"]);
    $im =  @imagecreatetruecolor($w, $h)
    or die ("Kann keinen neuen GD-Bild-Stream erzeugen");
    $background_color = ImageColorAllocate ($im, 255, 255, 255);
    imagefill($im, 0, 0, $background_color);
    imagesetthickness($im , 5 );
    $color = ImageColorAllocate ($im, 0, 0, 0);
    
    $sens = rand(1, 3);
    $x = rand(1, $w);
    $y = rand(1, $h);
    
    
    for ($i = 1; $i < $w; $i+=($w/10)) {
        for ($j = 1; $j < $h; $j+=($h/10)) {
            
            imageellipse ($im, $i * 2 + 70, $j * 2 + 70, abs($sens * ($x - 250)), abs($sens * ($y - 250)), $color);
            imageellipse ($im, $i * 2 + 70, $j * 2 + 70, abs($sens * ($x - 250))-1, abs($sens * ($y - 250))-1, $color);
            
            
            imageellipse ($im, $i * 2 + 70, $j * 2 + 70, abs($sens * ($y - 250)), abs($sens * ($x - 250)), $color);
            imageellipse ($im, $i * 2 + 70, $j * 2 + 70, abs($sens * ($y - 250))-1, abs($sens * ($x - 250))-1, $color);
        }
    }
        
    
    
    
    ImagePNG ($im);
?>