// vars/createSpringBootService.groovy

def call() {
    echo 'Checking if Spring Boot service exists...'
    
    sh '''
    SERVICE_FILE=/etc/systemd/system/springboot-app.service

    # Check if the service file already exists
    if [ ! -f "$SERVICE_FILE" ]; then
        echo "Service file does not exist. Creating springboot-app.service..."
        
        # Create the service file
        sudo bash -c "cat > $SERVICE_FILE" << 'EOF'
[Unit]
Description=Spring Boot Application
After=network.target

[Service]
User=ubuntu
WorkingDirectory=/home/ubuntu/workspace/test
ExecStart=/usr/bin/java -jar /home/ubuntu/workspace/test/target/hello-world-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
StandardOutput=append:/home/ubuntu/workspace/test/springboot.log
StandardError=append:/home/ubuntu/workspace/test/springboot-error.log
Restart=always

[Install]
WantedBy=multi-user.target
EOF

        # Reload systemd to apply changes
        echo "Reloading systemd daemon..."
        sudo systemctl daemon-reload
    else
        echo "Service file already exists. Skipping creation."
    fi
    '''
}
