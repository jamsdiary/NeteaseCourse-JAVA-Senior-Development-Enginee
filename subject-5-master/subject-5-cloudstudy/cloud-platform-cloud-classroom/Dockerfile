#Dockerfile文件
FROM registry.gz.cvte.cn/library/node:8.9.3
# Create app directory
RUN mkdir -p /home/Service
WORKDIR /home/Service
# Bundle app source
COPY . /home/Service
RUN npm install
EXPOSE 3000
CMD [ "npm", "run dev" ]

