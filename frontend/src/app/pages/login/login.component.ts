import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth'; 
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule // Adicionado para o link de cadastro
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  isLoading = false;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.loginForm.valid && !this.isLoading) {
      this.isLoading = true;
      this.errorMessage = null;

      this.authService.login(this.loginForm.value).subscribe({
        next: (response: any) => { 
          console.log('Login bem-sucedido!', response);
          this.router.navigate(['/diario']);
          this.isLoading = false;
        },
        error: (err: any) => {
          console.error('Falha no login', err);
          this.errorMessage = "E-mail ou senha incorretos.";
          this.isLoading = false;
        }
      });
    }
  }
}