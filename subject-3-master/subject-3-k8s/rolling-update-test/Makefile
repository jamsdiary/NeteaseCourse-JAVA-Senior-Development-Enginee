all: build push
.PHONY: build push

TAG = v2

# Build for linux amd64
build:
	docker build -t 192.168.100.249/study/hello-node:${TAG} .

# Push to harbor
push:
	docker push 192.168.100.249/study/hello-node:${TAG}

