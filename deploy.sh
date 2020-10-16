#:!/bin/bash
mv $HOME/pw/jp-sinsege-server/jp-sinsege-server.jar $HOME/pw/jp-sinsege-server/jp-sinsege-server.jar.bak
mv $HOME/upload/jp-sinsege-server.jar $HOME/pw/jp-sinsege-server/jp-sinsege-server.jar

$HOME/pw/jp-sinsege-server/shutdown.sh
$HOME/pw/jp-sinsege-server/startup.sh