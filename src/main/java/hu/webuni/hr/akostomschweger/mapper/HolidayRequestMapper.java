package hu.webuni.hr.akostomschweger.mapper;

import hu.webuni.hr.akostomschweger.dto.HolidayRequestDto;
import hu.webuni.hr.akostomschweger.model.HolidayRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import javax.validation.Valid;
import java.util.List;

@Mapper(componentModel = "spring")
public interface HolidayRequestMapper {

    List<HolidayRequestDto> holidayRequestsToDtos(List<HolidayRequest> holidayRequests);

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "approver.id", target = "approverId")
    HolidayRequestDto holidayRequestToDto(HolidayRequest holidayRequest);

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "approver", ignore = true)
    HolidayRequest dtoToHolidayRequest(@Valid HolidayRequestDto holidayRequestDto);

    List<HolidayRequest> dtosToHolidayRequests(List<HolidayRequestDto> holidayRequestDtos);


}
