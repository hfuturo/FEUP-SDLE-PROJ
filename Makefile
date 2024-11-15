MAKEFLAGS += --always-make

protobufs: 
	@-protoc -I=./common/protobufs --java_out=./backend/src/main/java/ ./common/protobufs/*.proto
