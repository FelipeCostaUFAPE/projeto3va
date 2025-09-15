import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiaryList } from './diary-list.component';

describe('DiaryList', () => {
  let component: DiaryList;
  let fixture: ComponentFixture<DiaryList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DiaryList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiaryList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
