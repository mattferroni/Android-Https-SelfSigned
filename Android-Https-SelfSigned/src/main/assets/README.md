### How to create a self-signed SSL Certificate ###
Reference: http://www.akadia.com/services/ssh_test_certificate.html

1) Step 1: Generate a Private Key
    openssl genrsa -des3 -out server.key 1024

2) Step 2: Generate a CSR (Certificate Signing Request)
    openssl req -new -key server.key -out server.csr

3) Step 3: Remove Passphrase from Key
    cp server.key server.key.org
    openssl rsa -in server.key.org -out server.key

4) Step 4: Generating a Self-Signed Certificate
    openssl x509 -req -days 3650 -in server.csr -signkey server.key -out server.crt

5) Step 5: Installing the Private Key and Certificate (depends on the Web server you use)