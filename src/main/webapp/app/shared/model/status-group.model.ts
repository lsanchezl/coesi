export interface IStatusGroup {
  id?: number;
  name?: string;
  keyStatus?: string;
}

export class StatusGroup implements IStatusGroup {
  constructor(public id?: number, public name?: string, public keyStatus?: string) {}
}
