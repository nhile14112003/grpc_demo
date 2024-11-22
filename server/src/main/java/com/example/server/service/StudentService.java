package com.example.server.service;

import java.util.HashMap;
import java.util.Map;

import com.example.grpc_demo.*;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class StudentService {

    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(9090)
                .addService(new StudentServiceImpl())
                .build();

        System.out.println("Server is starting...");
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server since JVM is shutting down");
            server.shutdown();
        }));

        server.awaitTermination();
    }

    static class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

        private static final Map<String, Student> studentDatabase = new HashMap<>();

        @Override
        public void createStudent(CreateStudentRequest request,
                StreamObserver<CreateStudentResponse> responseObserver) {
            Student student = request.getStudent();
            studentDatabase.put(student.getId(), student);

            CreateStudentResponse response = CreateStudentResponse.newBuilder()
                    .setMessage("Student created with ID: " + student.getId())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getStudent(GetStudentRequest request, StreamObserver<GetStudentResponse> responseObserver) {
            String studentId = request.getId();
            Student student = studentDatabase.get(studentId);

            GetStudentResponse.Builder responseBuilder = GetStudentResponse.newBuilder();

            if (student != null) {
                responseBuilder.setStudent(student);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }

        @Override
        public void updateStudent(UpdateStudentRequest request,
                StreamObserver<UpdateStudentResponse> responseObserver) {
            Student student = request.getStudent();
            studentDatabase.put(student.getId(), student);

            UpdateStudentResponse response = UpdateStudentResponse.newBuilder()
                    .setMessage("Student updated with ID: " + student.getId())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void deleteStudent(DeleteStudentRequest request,
                StreamObserver<DeleteStudentResponse> responseObserver) {
            String studentId = request.getId();
            studentDatabase.remove(studentId);

            DeleteStudentResponse response = DeleteStudentResponse.newBuilder()
                    .setMessage("Student deleted with ID: " + studentId)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}