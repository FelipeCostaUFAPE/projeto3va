import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DiaryService } from '../../services/diary';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-diary-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './diary-list.component.html',
  styleUrls: ['./diary-list.component.css']
})
export class DiaryListComponent implements OnInit {
  entries: any[] = [];
  newEntryForm: FormGroup;
  searchForm: FormGroup;

  constructor(private diaryService: DiaryService, private fb: FormBuilder) {
    this.newEntryForm = this.fb.group({
      conteudo: ['', Validators.required],
      humor: [null],
      rascunho: [false]
    });

    this.searchForm = this.fb.group({
      palavraChave: [''],
      data: ['']
    });
  }

  ngOnInit(): void {
    this.loadEntries();
  }

  loadEntries(palavraChave?: string, data?: string): void {
    this.diaryService.getEntries(palavraChave, data).subscribe({
      next: (data: any) => { this.entries = data; },
      error: (err: any) => console.error('Erro ao buscar entradas', err)
    });
  }

  onSearch(): void {
    const { palavraChave, data } = this.searchForm.value;
    this.loadEntries(palavraChave, data);
  }

  clearSearch(): void {
    this.searchForm.reset({ palavraChave: '', data: '' });
    this.loadEntries();
  }

  onSubmitNewEntry(): void {
    if (this.newEntryForm.valid) {
      this.diaryService.createEntry(this.newEntryForm.value).subscribe({
        next: () => {
          alert('Entrada criada com sucesso!');
          this.newEntryForm.reset({ rascunho: false });
          this.loadEntries();
        },
        error: (err: any) => console.error('Erro ao criar entrada', err)
      });
    }
  }
}