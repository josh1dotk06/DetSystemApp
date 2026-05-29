package config

import (
	"log" //used to print msg to terminal
	"os" //operating system ackage
	"github.com/joho/godotenv" //read env file, gives us godotenv.Load
)

//custom ds (we know this bruh)
//bundling all 3 secrets into one clean structure
type Config struct {
	ServerPort string
	DatabaseURL string
	JWTSecret string

}


//uses godotenv package to read the env and load its secrets
func Load() Config { 
	err := godotenv.Load();
	if err != nil{
		log.Println("No env file found, using system environment variables")
	}

	return Config{
		ServerPort: getEnv("SERVER_PORT", "8080"),
		DatabaseURL: getEnv("DATABASE_URL", ""),
		JWTSecret: getEnv("JWT_SECRET", "dev_secret")
	}
}

func getEnv(key string, fallback string) string { 
	value := os.GetEnv(key)
	if value == ""{
		return fallback
	}
	return value
}