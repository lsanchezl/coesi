
export interface IPerformanceIndicator {
  id?: number;
  name?: string;
}

export class PerformanceIndicator implements IPerformanceIndicator {
  constructor(public id?: number, public name?: string) {}
}
