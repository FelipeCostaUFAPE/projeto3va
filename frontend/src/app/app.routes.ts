import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { DiaryListComponent } from './pages/diary-list/diary-list.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    // Rota padrão: redireciona para a lista do diário
    { path: '', redirectTo: '/diario', pathMatch: 'full' },

    // Rota para a página de login
    { path: 'login', component: LoginComponent },

    // Rota para a página de cadastro
    { path: 'cadastro', component: RegisterComponent },

    // Rota para a página principal do diário
    { path: 'diario', component: DiaryListComponent, canActivate: [authGuard] }
];