package com.mansour.newdemo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mansour.newdemo.repository.CarRepository;
import com.mansour.newdemo.exception.ResourceNotFoundException;
import com.mansour.newdemo.model.Car;


@RestController
@RequestMapping("/api/v1")
public class CarController {

	@Autowired
	private CarRepository carRepository;
	
	// get ALL
	@GetMapping("/seifamily")
	public List<Car> getAllCar(){
		return this.carRepository.findAll();
	}
	
	// Get one
	 @GetMapping("/seifamily/{id}")
	    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId)
	        throws ResourceNotFoundException {
	        Car car = carRepository.findById(carId)
	          .orElseThrow(() -> new ResourceNotFoundException("Presone not found for this id :: " + carId));
	        return ResponseEntity.ok().body(car);
	    }
	    
	 
	 // Post one
	    @PostMapping("/seifamily")
	    public Car createCar(@Valid @RequestBody Car car) {
	        return carRepository.save(car);
	    }
	    
	    
	    // Update one
	    @PutMapping("/seifamily/{id}")
	    public ResponseEntity<Car> updateCar(@PathVariable(value = "id") Long carId,
	         @Valid @RequestBody Car carDetails) throws ResourceNotFoundException {
	    	// First get the car
	        Car car = carRepository.findById(carId)
	        .orElseThrow(() -> new ResourceNotFoundException("Presone not found for this id :: " + carId));
	        // then update value
	        car.setEmail(carDetails.getEmail());
	        car.setLastName(carDetails.getLastName());
	        car.setFirstName(carDetails.getFirstName());
	        final Car updatedCar = carRepository.save(car);
	        return ResponseEntity.ok(updatedCar);
	    }
	    
	    
	    // delete one
	    @DeleteMapping("/seifamily/{id}")
	    public Map<String, Boolean> deleteCar(@PathVariable(value = "id") Long carId)
	         throws ResourceNotFoundException {
	    	// first get the car
	        Car car = carRepository.findById(carId)
	       .orElseThrow(() -> new ResourceNotFoundException("Presone not found for this id :: " + carId));
	        // then delete it
	        carRepository.delete(car);
	        
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }
	}
