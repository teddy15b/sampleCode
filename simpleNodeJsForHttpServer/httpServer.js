var sys = require ('sys'),
url = require('url'),
http = require('http'),
qs = require('querystring');

http.createServer(function (req, res) {
    if(req.method=='POST') {
            var body='';
            req.on('data', function (data) {
                body +=data;
            });
            req.on('end',function(){
                
                var POST =  qs.parse(body);
                console.log(POST);
            });
    }
    else if(req.method=='GET') {
        var url_parts = url.parse(req.url,true);
        console.log(url_parts.query);
        responseMsg(res);
    }
    
    
}).listen(7788, "127.0.0.1");

function responseMsg(response){
  response.writeHead(200, {"Content-Type": "text/html"});  
  response.end(); 

}
