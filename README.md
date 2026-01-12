# Finance Tracker

## Description
Simple finance tracker that can track income, expenses, assets, budgets and financial goals and even help users split expenses with others. It aims to analyse user's financial patterns and provide personalised advice using AI. Lastly, it will integrate with a Telegram bot for convenient access and usage.

## Features
- Track income, expenses, assets, budgets, and financial goals
- Analyse financial patterns and provide personalised advice using AI
- Split expenses with others
- Integration with a Telegram bot for easy access

## Technologies Used
- Java Spring Boot
- Angular

## Local Development

1. Start the PostgreSQL container:
	```bash
	docker compose up -d
	```
2. Run the Spring Boot backend:
	```bash
	cd backend
	./mvnw spring-boot:run
	```
3. When finished, stop the container:
	```bash
	docker compose down
	```