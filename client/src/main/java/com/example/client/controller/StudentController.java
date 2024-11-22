package com.example.client.controller;

import com.example.grpc_demo.UpdateStudentRequest;
import com.example.grpc_demo.UpdateStudentResponse;
import com.example.grpc_demo.CreateStudentRequest;
import com.example.grpc_demo.CreateStudentResponse;
import com.example.grpc_demo.DeleteStudentRequest;
import com.example.grpc_demo.DeleteStudentResponse;
import com.example.grpc_demo.GetStudentRequest;
import com.example.grpc_demo.GetStudentResponse;
import com.example.grpc_demo.Student;
import com.example.client.dto.StudentDTO;
import com.example.client.service.StudentClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentClientService studentClientService;

    @PostMapping("/create")
    public String createStudent(@RequestBody StudentDTO studentDTO) {
        CreateStudentRequest request = CreateStudentRequest.newBuilder()
                .setStudent(Student.newBuilder()
                        .setId(studentDTO.getId())
                        .setName(studentDTO.getName())
                        .setDob(studentDTO.getDob())
                        .setAddress(studentDTO.getAddress())
                        .setPhone(studentDTO.getPhone())
                        .setEmail(studentDTO.getEmail())
                        .build())
                .build();

        CreateStudentResponse response = studentClientService.createStudent(request);
        return response.getMessage();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable String id) {
        GetStudentRequest request = GetStudentRequest.newBuilder().setId(id).build();
        GetStudentResponse response = studentClientService.getStudent(request);

        if (response.hasStudent()) {
            return response.getStudent();
        } else {
            return null;
        }
    }

    @PutMapping("/update")
    public String updateStudent(@RequestBody StudentDTO studentDTO) {
        UpdateStudentRequest request = UpdateStudentRequest.newBuilder()
                .setStudent(Student.newBuilder()
                        .setId(studentDTO.getId())
                        .setName(studentDTO.getName())
                        .setDob(studentDTO.getDob())
                        .setAddress(studentDTO.getAddress())
                        .setPhone(studentDTO.getPhone())
                        .setEmail(studentDTO.getEmail())
                        .build())
                .build();

        UpdateStudentResponse response = studentClientService.updateStudent(request);
        return response.getMessage();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        DeleteStudentRequest request = DeleteStudentRequest.newBuilder().setId(id).build();
        DeleteStudentResponse response = studentClientService.deleteStudent(request);
        return response.getMessage();
    }
}
