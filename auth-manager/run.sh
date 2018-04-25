sudo docker build -t auth-system/manager .
sudo docker run -p 1090:1090 --name manager auth-system/manager
