AUTH-SERVICE

1. Crear base de datos auth_db con el script SQL incluido.
2. Abrir este proyecto en IntelliJ.
3. Verificar application.properties.
4. Ejecutar la aplicación.
5. Probar:
   GET  http://localhost:8081/auth/publico
   POST http://localhost:8081/auth/login

Body:
{
  "username": "admin",
  "password": "1234"
}
