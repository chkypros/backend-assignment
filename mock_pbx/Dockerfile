FROM node:12

MAINTAINER George Nikolaides "g.nik@impactechs.com"

WORKDIR /usr/src/app

COPY package*.json ./

# Install app dependencies
RUN npm install

# Bundle app source
COPY . .

EXPOSE 3030

CMD [ "npm", "start" ]
