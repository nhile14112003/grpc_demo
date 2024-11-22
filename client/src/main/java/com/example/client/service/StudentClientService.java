package com.example.client.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import com.example.grpc_demo.CreateStudentRequest;
import com.example.grpc_demo.CreateStudentResponse;
import com.example.grpc_demo.DeleteStudentRequest;
import com.example.grpc_demo.DeleteStudentResponse;
import com.example.grpc_demo.GetStudentRequest;
import com.example.grpc_demo.GetStudentResponse;
import com.example.grpc_demo.StudentServiceGrpc;
import com.example.grpc_demo.UpdateStudentRequest;
import com.example.grpc_demo.UpdateStudentResponse;

@Service
public class StudentClientService {

    private final ManagedChannel channel;
    private final StudentServiceGrpc.StudentServiceBlockingStub blockingStub;

    public StudentClientService() {
        channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        blockingStub = StudentServiceGrpc.newBlockingStub(channel);
    }

    public CreateStudentResponse createStudent(CreateStudentRequest request) {
        return blockingStub.createStudent(request);
    }

    public GetStudentResponse getStudent(GetStudentRequest request) {
        return blockingStub.getStudent(request);
    }

    public UpdateStudentResponse updateStudent(UpdateStudentRequest request) {
        return blockingStub.updateStudent(request);
    }

    public DeleteStudentResponse deleteStudent(DeleteStudentRequest request) {
        return blockingStub.deleteStudent(request);
    }

    public void shutdown() {
        channel.shutdown();
    }
}
