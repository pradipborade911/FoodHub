package com.foodhub.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foodhub.dto.TiffinDto;
import com.foodhub.entities.Tiffin;
import com.foodhub.entities.Vendor;
import com.foodhub.execptions.ResourceNotFoundException;
import com.foodhub.repository.TiffinRepository;
import com.foodhub.repository.VendorRepository;

@Service
@Transactional
public class TiffinServiceImpl implements TiffinService {

	@Autowired
	private TiffinRepository tiffinRepo;

	@Autowired
	private VendorRepository vendorRepo ;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TiffinDto createTiffin(TiffinDto tiffinDto) {
		Vendor vendor = vendorRepo.findById(tiffinDto.getVendorId())
				.orElseThrow(()-> new ResourceNotFoundException("Invalid Vendor Id")) ;
		Tiffin tiffin = modelMapper.map(tiffinDto, Tiffin.class) ;
		tiffin.setVendor(vendor);
		tiffin = tiffinRepo.save(tiffin);
		return modelMapper.map(tiffin, TiffinDto.class);
	}

	@Override
	public List<TiffinDto> getAllTiffins() {
		return tiffinRepo.findAll()
				.stream()
				.map(tiffin->modelMapper.map(tiffin, TiffinDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public TiffinDto getTiffinById(Long tiffinId) {
		return modelMapper.map(tiffinRepo.findById(tiffinId), TiffinDto.class);
	}

	@Override
	public List<TiffinDto> getTiffinsByVendorId(Long vendorId) {
		List<Tiffin> tiffinList = tiffinRepo.findAllByVendorId(vendorId)
				.orElseThrow( () -> new ResourceNotFoundException("This vendor has no tiffins.") );

		return tiffinList.stream()
								.map(tiffin->modelMapper.map(tiffin, TiffinDto.class))
								.collect(Collectors.toList());
	}

	@Override
	public TiffinDto updateTiffinDetails(TiffinDto tiffinDto) {
		tiffinRepo.findById(tiffinDto.getId())
						.orElseThrow(()->new ResourceNotFoundException("Tiffin not found"));
		Tiffin tiffin = tiffinRepo.save(modelMapper.map(tiffinDto, Tiffin.class));
		return modelMapper.map(tiffin, TiffinDto.class);
	}

	@Override
	public String deleteTiffinById(Long tiffinId) {
		Tiffin tiffin = tiffinRepo.findById(tiffinId)
				.orElseThrow(()->new ResourceNotFoundException("Tiffin not found"));
		tiffinRepo.deleteById(tiffinId);
		return "Tiffin " + tiffin.getName() + " has been removed!";		
	}

	@Override
	public TiffinDto blockTiffinById(Long tiffinId) {
		Tiffin tiffin = tiffinRepo.findById(tiffinId)
				.orElseThrow(()->new ResourceNotFoundException("Invalid Tiffin Id"));
		tiffin.setBreakLunchDinner(0);
		return modelMapper.map(tiffinRepo.save(tiffin), TiffinDto.class);
	}

	@Override
	public List<TiffinDto> getAllAvailableTiffins() {
		List<Tiffin> tiffinList = tiffinRepo.findAllAvailabletiffins().
				orElseThrow(()-> new ResourceNotFoundException("No tiffins available Today From any Vendor"));
		List<TiffinDto> tiffinDtoList = tiffinList.stream().map(tiffin->modelMapper.map(tiffin, TiffinDto.class))
				.collect(Collectors.toList());
		return tiffinDtoList;
	}

	@Override
	public List<TiffinDto> getAllUnAvailableTiffins() {
		List<Tiffin> tiffinList = tiffinRepo.findAllUnavailabletiffins().
				orElseThrow(()-> new ResourceNotFoundException("No Unavailable tiffin found"));
		List<TiffinDto> tiffinDtoList = tiffinList.stream().map(tiffin->modelMapper.map(tiffin, TiffinDto.class))
				.collect(Collectors.toList());
		return tiffinDtoList;
	}

}
