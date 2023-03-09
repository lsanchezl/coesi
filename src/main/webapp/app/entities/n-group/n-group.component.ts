import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { INGroup } from 'app/shared/model/n-group.model';
import { TranslateService } from '@ngx-translate/core';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { NGroupService } from './n-group.service';
import { NGroupDeleteDialogComponent } from './n-group-delete-dialog.component';
import { ICareer } from 'app/shared/model/career.model';
import { Career } from 'app/shared/model/career.model';
import { CareerService } from 'app/entities/career/career.service';
import { IRoom } from 'app/shared/model/room.model';
import { Room } from 'app/shared/model/room.model';
import { RoomService } from 'app/entities/room/room.service';
import { ISchoolCycle } from 'app/shared/model/school-cycle.model';
import { SchoolCycle } from 'app/shared/model/school-cycle.model';
import { SchoolCycleService } from 'app/entities/school-cycle/school-cycle.service';

type SelectableEntity = ISchoolCycle | ICareer | IRoom;

@Component({
  selector: 'jhi-n-group',
  templateUrl: './n-group.component.html',
})
export class NGroupComponent implements OnInit, OnDestroy {
  nGroups?: INGroup[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  careers: ICareer[] = [];
  schoolcycles: ISchoolCycle[] = [];
  rooms: IRoom[] = [];
  selectedCareer: Career = new Career();
  selectedSchoolCycle: SchoolCycle = new SchoolCycle();
  selectedRoom: Room = new Room();
  combosLoaded = false;
  allCareersOption: Career = new Career();
  allSchoolCyclesOption: SchoolCycle = new SchoolCycle();
  allRoomsOption: Room = new Room();
  allLabel!: string;

  constructor(
    protected nGroupService: NGroupService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected careerService: CareerService,
    protected schoolCycleService: SchoolCycleService,
    protected roomService: RoomService,
    protected modalService: NgbModal,
    protected translate: TranslateService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.allLabel = this.translate.instant("general.all");
    
    this.allCareersOption.id = -1;
    this.allCareersOption.name = this.allLabel;
    this.selectedCareer = this.allCareersOption;
    
    this.allSchoolCyclesOption.id = -1;
    this.allSchoolCyclesOption.name = this.allLabel;
    this.selectedSchoolCycle = this.allSchoolCyclesOption;
    
    this.allRoomsOption.id = -1;
    this.allRoomsOption.name = this.allLabel;
    this.selectedRoom = this.allRoomsOption;
    
    if (this.combosLoaded) {
        this.loadNGroups(page, dontNavigate);
    } else { // primera vez que entra a la página, cargamos los combos
        this.careerService.query().subscribe((res: HttpResponse<ICareer[]>) => {
            this.careers = res.body || [];
            
            this.schoolCycleService.query().subscribe((resSC: HttpResponse<ISchoolCycle[]>) => {
                this.schoolcycles = resSC.body || [];
                this.roomService.query().subscribe((resR: HttpResponse<IRoom[]>) => {
                    this.rooms= resR.body || [];
                this.combosLoaded = true;
                this.loadNGroups(page, dontNavigate);
                 });
            });

         });
        }
    }

    loadNGroups(page?: number, dontNavigate?: boolean): void{
        const pageToLoad: number = page || this.page || 1;
        this.nGroupService
            .query(this.selectedCareer.id, this.selectedSchoolCycle.id, this.selectedRoom.id, {
              page: pageToLoad - 1,
              size: this.itemsPerPage,
              sort: this.sort(),
            })
            .subscribe(
              (resNG: HttpResponse<INGroup[]>) => this.onSuccess(resNG.body, resNG.headers, pageToLoad, !dontNavigate),
              () => this.onError()
            );
    }
  
   changeCareer(newCareer: Career): void {
    this.selectedCareer = newCareer;
    this.page = 1;
    this.loadNGroups();
   }

   changeSchoolCycle(newSchoolCycle: SchoolCycle): void {
    this.selectedSchoolCycle = newSchoolCycle;
    this.page = 1;
    this.loadNGroups();
   }

   changeRoom(newRoom: Room): void {
    this.selectedRoom = newRoom;
    this.page = 1;
    this.loadNGroups();
   }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInNGroups();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INGroup): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }
  
  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  registerChangeInNGroups(): void {
    this.eventSubscriber = this.eventManager.subscribe('nGroupListModification', () => this.loadPage());
  }

  delete(nGroup: INGroup): void {
    const modalRef = this.modalService.open(NGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nGroup = nGroup;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: INGroup[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/n-group'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.nGroups = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
