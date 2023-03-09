import { Moment } from 'moment';
import { IPerformanceIndicator } from 'app/shared/model/performance-indicator.model';
import { INGroup } from 'app/shared/model/n-group.model';

export interface IEvaluationCriteria {
  id?: number;
  description?: string;
  percentage?: number;
  deliveryDate?: Moment;
  performanceIndicator?: IPerformanceIndicator;
  nGroup?: INGroup;
}

export class EvaluationCriteria implements IEvaluationCriteria {
  constructor(
    public id?: number,
    public description?: string,
    public percentage?: number,
    public deliveryDate?: Moment,
    public performanceIndicator?: IPerformanceIndicator,
    public nGroup?: INGroup
  ) {}
}
