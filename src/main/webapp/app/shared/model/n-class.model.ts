import { ICareer } from 'app/shared/model/career.model';

export interface INClass {
  id?: number;
  name?: string;
  keyClass?: string;
  area?: string;
  division?: string;
  units?: number;
  nOrder?: number;
  career?: ICareer;
}

export class NClass implements INClass {
  constructor(
    public id?: number,
    public name?: string,
    public keyClass?: string,
    public area?: string,
    public division?: string,
    public units?: number,
    public nOrder?: number,
    public career?: ICareer
  ) {}
}
