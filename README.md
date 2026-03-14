# ⚕️ MedShield | Integrated Health Risk Analytics

**MedShield** is an enterprise-grade Clinical Decision Support System (CDSS) designed to automate risk assessment for chronic conditions. Developed with a focus on preventative healthcare, it bridges the gap between physical patient reports and digital health tracking.

## 🚀 Key Features
- **Dual-Engine Analysis:** Leverages clinical data to assess Hypertension and Diabetes risks simultaneously.
- **QR-Enabled Portability:** Generates secure PDF reports with embedded QR codes for instant digital record retrieval.
- **Microservices Ready:** Built with a Spring Boot backend and MySQL, fully containerized using Docker.
- **Responsive Clinical UI:** Professional dashboard with high-contrast modes for medical environments.

## 🛠 Tech Stack
- **Backend:** Java 17, Spring Boot, Spring Data JPA
- **Frontend:** HTML5, CSS3 (Bootstrap), JavaScript
- **Database:** MySQL / H2
- **DevOps:** Docker, Docker Compose, Maven
- **Security:** Session-based authentication & logic-level risk validation

## 📊 Risk Engine Logic
The system uses a probability-based classification model:
- **LOW RISK (< 0.30):** Routine check-up suggested.
- **MODERATE RISK (0.30 - 0.69):** Triggers preventative lifestyle warnings.
- **HIGH RISK (≥ 0.70):** Flags immediate clinical intervention.

## 📦 Deployment
Run the entire stack using Docker:
```bash
docker-compose up --build
