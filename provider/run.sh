sudo docker build -t auth-system/provider .
sudo docker run -p 1095:1095 --name provider auth-system/provider
