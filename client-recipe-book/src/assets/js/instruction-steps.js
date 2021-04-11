$(document).ready(function() {
    var max_fields = 50;
    var wrapper2   = $(".wrapper-2");
    var add_button_instruction = $(".add_fields_instruction");
    var x = 1;  
    $(add_button_instruction).click(function(e) {
        e.preventDefault();
        if(x < max_fields) { 
            x++;
            $(wrapper2)
            .append(
                '<div class="instruction-steps added-steps"><h6 class="step-number">Шаг '+ x + '</h6> ' +
                '<textarea class="description" id="step_description_'+ x + ' " rows="4"></textarea> ' +
                '<button class="add_fields_instruction instruction_remove_field uk-button">Удалить шаг</button></div>');
        }
    });
    $(wrapper2).on("click",".instruction_remove_field", function(e) { 
        e.preventDefault();
        $(this).parent('div').remove();
        x--;
    })
});