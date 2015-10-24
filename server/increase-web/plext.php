<?php


$version = $_GET['version'];
$csrftoken = $_GET['csrftoken'];
$sacsid = $_GET['sacsid'];


$url = "https://www.ingress.com/r/getPlexts";
$cookie = "csrftoken=$csrftoken; __utma=24037858.253737590.1413652003.1416056245.1416650976.48; __utmc=24037858; __utmz=24037858.1413652003.1.1.utmcsr=duckduckgo.com|utmccn=(referral)|utmcmd=referral|utmcct=/; SACSID=$sacsid; ingress.intelmap.lat=50.1025584721709; ingress.intelmap.lng=8.663159608840942; ingress.intelmap.zoom=17";
$postdata ='{"minLatE6":50100453,"minLngE6":8654147,"maxLatE6":50104664,"maxLngE6":8672172,"minTimestampMs":-1,"maxTimestampMs":-1,"tab":"all","v":"'. $version . '","b":"null","c":"null"}';
$htmlHeader = array(
    'origin: https://www.ingress.com',
    'referer: https://www.ingress.com/intel',
    'accept: application/json, text/javascript, */*; q=0.01',
    'accept-encoding: gzip, deflate',
    'accept-language: en-US,en;q=0.8',
    'x-requested-with: XMLHttpRequest',
    'x-csrftoken: '. $csrftoken,
    'user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/37.0.2062.120 Chrome/37.0.2062.120 Safari/537.36',
    'content-type: application/json; charset=UTF-8',
    'cookie: '. $cookie
    );

$curlSession = curl_init();

curl_setopt ($curlSession, CURLOPT_URL, $url);
curl_setopt($curlSession, CURLOPT_ENCODING , "gzip");
curl_setopt($curlSession, CURLOPT_HTTPHEADER, $htmlHeader);

curl_setopt ($curlSession, CURLOPT_POST, 1);
curl_setopt ($curlSession, CURLOPT_POSTFIELDS, $postdata);


  $response = curl_exec($curlSession);
  $responseInfo = curl_getinfo($curlSession);
  $headerSize = curl_getinfo($curlSession, CURLINFO_HEADER_SIZE);
  curl_close($curlSession);

  $responseHeaders = substr($response, 0, $headerSize);
  $responseBody = substr($response, $headerSize);

  echo $responseBody;

?>

