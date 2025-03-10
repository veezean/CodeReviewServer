FROM openjdk:8
COPY release/CodeReviewServer_v4.2.1 app/
EXPOSE 23560

# JAVA
ENV JAVA_COMMAND=""
ENV JAVA_OPTS=""

# MONGO
ENV MONGODB_HOST=""
ENV MONGODB_PORT=""
ENV MONGODB_DATABASE=""
ENV MONGODB_USERNAME=""
ENV MONGODB_PASSWORD=""
ENV MONGODB_AUTH_DATABASE="admin"

# WEBHOOK
ENV WEBHOOK_URL=""

# RUN
ENTRYPOINT java ${JAVA_COMMAND} -jar app/com.veezean.codereview.server-4.2.1.jar --spring.profiles.active=PROD  --spring.data.mongodb.host=${MONGODB_HOST} --spring.data.mongodb.port=${MONGODB_PORT} --spring.data.mongodb.database=${MONGODB_DATABASE} --spring.data.mongodb.username=${MONGODB_USERNAME} --spring.data.mongodb.password=${MONGODB_PASSWORD} --spring.data.mongodb.authentication-database=${MONGODB_AUTH_DATABASE} --application.notice.webhook.url=${WEBHOOK_URL} ${JAVA_OPTS}