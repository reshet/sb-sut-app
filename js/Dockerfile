FROM node:20-alpine3.18
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3200
CMD [ "node", "index.js" ]