package dev.samuel.projectgrocery.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record FornecedorRequest(

    @NotBlank(message = "O campo razão social é obrigatório")
    @Size(max = 150, message = "O campo razão social deve ter no máximo 150 caracteres")
    String razaoSocial,

    @NotBlank(message = "O campo CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos")
    @CNPJ(message = "CNPJ inválido")
    String cnpj,

    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 100, message = "O campo email deve ter no máximo 100 caracteres")
    String email,

    @Size(max = 20, message = "O campo telefone deve ter no máximo 20 caracteres")
    String telefone

) {}
