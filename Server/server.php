<?php  

$message_queue_key = ftok(__FILE__, 'a'); 
$message_queue = msg_get_queue($message_queue_key, 0666);
msg_send($message_queue, 1, 12330236);

// 接受客户端请求，回复固定的响应内容  
function server_listen_socket ($address, $port, $isRead)  
{  
    // create, bind and listen to socket  
    $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
    if (! $socket) {  
        echo "failed to create socket:" . socket_strerror($socket) . "\n";  
        exit();  
    }  
      
    $bind_flag = socket_bind($socket, $address, $port);
    
    if (! $bind_flag) {  
        echo "failed to bind socket:" . socket_strerror($bind_flag) . "\n";  
        exit();  
    }  
      
    $backlog = 20;  
    $listen_flag = socket_listen($socket, $backlog); 
     
    if (! $listen_flag) {  
        echo "failed to listen to socket:" . socket_strerror($listen_flag) . "\n";  
        exit();  
    }  
      
    echo "waiting for clients to connect\n";  
    $message_queue_key = ftok(__FILE__, 'a'); 
    $message_queue = msg_get_queue($message_queue_key, 0666);
    while(1) {  
        $msgsock = socket_accept($socket) or  die("socket_accept() failed: reason: " . socket_strerror(socket_last_error()) . "/n");  
        echo "Read client data \n";  
        //socket_read函数会一直读取客户端数据,直到遇见\n,\t或者\0字符.PHP脚本把这写字符看做是输入的结束符.  
        $buf = socket_read($msgsock, 8192);  
        msg_receive($message_queue, 0, $message_type, 1024, $message, true, MSG_IPC_NOWAIT);
        $message = intval($message) + 1;
        msg_send($message_queue, 1, $message);
        echo "Received msg: $buf   $message\n";  
        

        //数据传送 向客户端写入返回结果  
        $msg = "welcome to lyt't server\n";  
        socket_write($msgsock, $msg, strlen($msg)) or die("socket_write() failed: reason: " . socket_strerror(socket_last_error()) ."/n");  
        //一旦输出被返回到客户端,父/子socket都应通过socket_close($msgsock)函数来终止  
        socket_close($msgsock);  
    }
    socket_close($socket);
}  
  
function run_server ()  
{
    $address = "115.28.84.22";  
    $port = 20460;
    $pids = array();
    for ( $i = 0; $i < 2; $i++) {
        $pids[$i] = pcntl_fork();
        if ($pids[$i] == 0) {
            server_listen_socket($address,$port+$i);
            exit();
        }
    }

}  
  
// server守护进程  
run_server();  