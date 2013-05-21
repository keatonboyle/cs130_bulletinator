<html>
<?php

   $data = array( type => 'GPS_RESPONSE',
                  hash => 'SOME_HASH',
                  curbuild => array(
                                    array(bldid => 'SOME_BLDID',
                                          name => 'SOME_NAME'),
                                    array(bldid => 'SOME_BLDID2',
                                          name => 'SOME_NAME2')
                                   )
                );

   var_dump(json_decode(json_encode($data), true));
?>
</html>