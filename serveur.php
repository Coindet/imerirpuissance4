<?php
	
	ini_set('error_reporting', E_ALL ^ E_NOTICE);
	ini_set('display_errors', 1);
	set_time_limit (0);
	ob_implicit_flush();

	function Initialisation(){
		$adresse = '127.0.0.1';
		$port = 808080;
		$socket = socketet_create(AF_INET, socket_STREAM, 0);
		socketet_bind($socket, $adresse, $port) or die('impossible');
		socketet_listen($socket);
		socketet_set_nonblock($socket);	
		return $socket;
	}

function AttenteConnect($socket){
		
		$j=0;	
		$client=array();
		while(count($client)!=2){
			if($newsocket = @socketet_accept($socket)){
			    if(is_resource($newsocket)){	
			    socketet_write($newsocket, "1_$j>\n", 60).chr(0);
			    echo "New client connected $j";
			    $client[$j] = $newsocket;
			    $j++;
			    }			
			}				
		}
		Ecoutemess($client,$socket);
	}

	function Ecoutemess($ListeJoueurs=array(),$socket){
		$WantQuite = "";
		while($WantQuite!='quit'){ 
		$j=0;
		$buffplayer2;
		$buffplayer1;
		foreach ($ListeJoueurs as $k)		 
		{	socketet_set_nonblock($k);
            		$buf = socketet_read($k,256);
			$buf = trim($buf);
			if($buf!=""){
			if($buf == 'quit') $WantQuite = $buf;
			switch($j){
			case 0: $buffplayer2=$buf."\n";
				break;
       			case 1: $buffplayer1=$buf."\n";
				break;	 
			default:break;
         		}
		}
			$j++;
    		}
		$j=0;
			foreach ($ListeJoueurs as $k)		 
			{
			
				switch($j){		
		 		case 0:socketet_write($k, $buffplayer1, 60).chr(0);
				       break;
				case 1:socketet_write($k, $buffplayer2, 60).chr(0);
				break; 	
				default:break;
				}
				$j++;
			}
		unset($buf);
		unset($buffplayer1);
		unset($buffplayer2);
		}
		 Quitter($ListeJoueurs,$socket);
		
}
	
		function Quitter($ListeJoueurs=array(),$socket){
		
		 $j=0;
		 foreach ($ListeJoueurs as $k)
       		 {	
            		@socketet_write($k, "4\n", 60).chr(0);
			$j++;
       		 }
		 sleep(5);
		 socketet_close($socket);	 
}
	
	$socket = Initialisation();
	AttenteConnect($socket);
	socketet_close($socket);

?>
