import { IGroupStudent } from 'app/shared/model/group-student.model';
import { IStatus } from 'app/shared/model/status.model';
import { IEvaluationCriteria } from 'app/shared/model/evaluation-criteria.model';

export interface IStudentEvaluation {
  id?: number;
  score?: number;
  groupStudent?: IGroupStudent;
  status?: IStatus;
  evaluationCriteria?: IEvaluationCriteria;
}

export class StudentEvaluation implements IStudentEvaluation {
  constructor(
    public id?: number,
    public score?: number,
    public groupStudent?: IGroupStudent,
    public status?: IStatus,
    public evaluationCriteria?: IEvaluationCriteria
  ) {}
}
