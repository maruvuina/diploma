package by.bsu.recipebook.mapper;

import by.bsu.recipebook.dto.InstructionDto;
import by.bsu.recipebook.entity.Instruction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstructionMapper {
    @Mapping(target = "stepNumber", source = "instructionDto.stepNumber")
    @Mapping(target = "description", source = "instructionDto.description")
    Instruction mapToInstruction(InstructionDto instructionDto);

    @Mapping(target = "stepNumber", source = "instruction.stepNumber")
    @Mapping(target = "description", source = "instruction.description")
    InstructionDto mapToInstructionDto(Instruction instruction);
}
