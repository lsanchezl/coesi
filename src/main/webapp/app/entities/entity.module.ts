import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'generation',
        loadChildren: () => import('./generation/generation.module').then(m => m.CoesiGenerationModule),
      },
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.CoesiStudentModule),
      },
      {
        path: 'n-class',
        loadChildren: () => import('./n-class/n-class.module').then(m => m.CoesiNClassModule),
      },
      {
        path: 'performance-indicator',
        loadChildren: () => import('./performance-indicator/performance-indicator.module').then(m => m.CoesiPerformanceIndicatorModule),
      },
      {
        path: 'evaluation-criteria',
        loadChildren: () => import('./evaluation-criteria/evaluation-criteria.module').then(m => m.CoesiEvaluationCriteriaModule),
      },
      {
        path: 'teacher',
        loadChildren: () => import('./teacher/teacher.module').then(m => m.CoesiTeacherModule),
      },
      {
        path: 'room',
        loadChildren: () => import('./room/room.module').then(m => m.CoesiRoomModule),
      },
      {
        path: 'n-group',
        loadChildren: () => import('./n-group/n-group.module').then(m => m.CoesiNGroupModule),
      },
      {
        path: 'type-attendance',
        loadChildren: () => import('./type-attendance/type-attendance.module').then(m => m.CoesiTypeAttendanceModule),
      },
      {
        path: 'modality',
        loadChildren: () => import('./modality/modality.module').then(m => m.CoesiModalityModule),
      },
      {
        path: 'status',
        loadChildren: () => import('./status/status.module').then(m => m.CoesiStatusModule),
      },
      {
        path: 'group-student',
        loadChildren: () => import('./group-student/group-student.module').then(m => m.CoesiGroupStudentModule),
      },
      {
        path: 'attendance',
        loadChildren: () => import('./attendance/attendance.module').then(m => m.CoesiAttendanceModule),
      },
      {
        path: 'career',
        loadChildren: () => import('./career/career.module').then(m => m.CoesiCareerModule),
      },
      {
        path: 'student-evaluation',
        loadChildren: () => import('./student-evaluation/student-evaluation.module').then(m => m.CoesiStudentEvaluationModule),
      },
      {
        path: 'school-cycle',
        loadChildren: () => import('./school-cycle/school-cycle.module').then(m => m.CoesiSchoolCycleModule),
      },
      {
        path: 'status-group',
        loadChildren: () => import('./status-group/status-group.module').then(m => m.CoesiStatusGroupModule),
      },
      {
        path: 'concept-income',
        loadChildren: () => import('./concept-income/concept-income.module').then(m => m.CoesiConceptIncomeModule),
      },
      {
        path: 'income',
        loadChildren: () => import('./income/income.module').then(m => m.CoesiIncomeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CoesiEntityModule {}
