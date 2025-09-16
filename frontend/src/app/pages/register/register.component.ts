import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  
  isLoading = false;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      nomeCompleto: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      senha: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  onSubmit() {
    if (this.registerForm.valid && !this.isLoading) {
      
      this.isLoading = true;
      this.errorMessage = null;

      this.authService.register(this.registerForm.value).subscribe({
        next: () => {
          console.log('Cadastro bem-sucedido!');
          alert('Cadastro realizado com sucesso! Você será redirecionado para o diário.');
          this.router.navigate(['/diario']);
          this.isLoading = false;
        },
        error: (err: any) => {
          console.error('Falha no cadastro', err);
          this.errorMessage = 'Não foi possível realizar o cadastro. Verifique os dados ou tente um e-mail diferente.';
          this.isLoading = false;
        }
      });
    }
  }
}