package main

import (
	"fmt"

	"detsystem-backend/internal/config"
	"detsystem-backend/internal/db"
	"log"
	"net/http"
)

func main() {
	//load secrets to our main
	cfg := config.Load()

	//initialize db handle and connection, use URL secret to
	//make connection
	database, err := db.Connect(cfg.DatabaseURL)
	if err != nil {
		log.Fatal("Database connection failed", err)
	}
	defer database.Close()

	mux := http.NewServeMux()

	mux.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		fmt.Fprintln(w, "Det System backend is running")
	})

	mux.HandleFunc("/health/db", func(w http.ResponseWriter, r *http.Request) {
		if err := database.Ping(); err != nil {
			http.Error(w, "Database is not connected", http.StatusInternalServerError)
			return
		}

		w.WriteHeader(http.StatusOK)
		fmt.Fprintln(w, "Database is connected")
	})

	//start the server
	addr := ":" + cfg.ServerPort
	log.Println("Server running on http://localhost" + addr)

	if err := http.ListenAndServe(addr, mux); err != nil {
		log.Fatal("Server fialed ", err)
	}

}
