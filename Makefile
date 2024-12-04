MAKEFLAGS += --always-make

protobufs: 
	@-protoc -I=./common/protobufs --java_out=./backend/src/main/java/	\
		./common/protobufs/dottedvalue.proto							\
		./common/protobufs/dotcontext.proto								\
		./common/protobufs/mvregister.proto								\
		./common/protobufs/ccounter.proto								\
		./common/protobufs/awset.proto									\
		./common/protobufs/nodeidentifier.proto							\
		./common/protobufs/awmap.proto									\
		./common/protobufs/hashcheck.proto								\
		./common/protobufs/hashringjoin.proto							\
		./common/protobufs/hashringlogoperation.proto					\
		./common/protobufs/hashringmsg.proto							\
		./common/protobufs/shoppinglistitem.proto						\
		./common/protobufs/message.proto								\
		./common/protobufs/documentrequest.proto						\
		./common/protobufs/document.proto								
