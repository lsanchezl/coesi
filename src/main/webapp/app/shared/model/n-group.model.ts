import { Moment } from 'moment';
import { ISchoolCycle } from 'app/shared/model/school-cycle.model';
import { ITeacher } from 'app/shared/model/teacher.model';
import { IStatusGroup } from 'app/shared/model/status-group.model';
import { INClass } from 'app/shared/model/n-class.model';
import { IRoom } from 'app/shared/model/room.model';
import { IModality } from 'app/shared/model/modality.model';

export interface INGroup {
  id?: number;
  name?: string;
  startDate?: Moment;
  endDate?: Moment;
  schoolCycle?: ISchoolCycle;
  teacher?: ITeacher;
  nClass?: INClass;
  room?: IRoom;
  modality?: IModality;
  statusGroup?: IStatusGroup;
  previousStatusGroup?: IStatusGroup;
  nextStatusGroup?: IStatusGroup;
}

export class NGroup implements INGroup {
  constructor(
    public id?: number,
    public name?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public schoolCycle?: ISchoolCycle,
    public teacher?: ITeacher,
    public nClass?: INClass,
    public room?: IRoom,
    public modality?: IModality,
    public statusGroup?: IStatusGroup,
    public previousStatusGroup?: IStatusGroup,
    public nextStatusGroup?: IStatusGroup
  ) {}
}
