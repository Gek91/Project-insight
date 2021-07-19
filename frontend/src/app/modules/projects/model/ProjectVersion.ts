import { Team } from "./Team";

export interface ProjectVersion {
    majorVersion: number;
    minorVersion: number;
    patchVersion: number;
    versionLabel: string,
    statusId: number,
    note?: string;
    releaseDate: Date;    
}

export enum ProjectVersionStatus {
    RELEASED = 1,
    SCHEDULED = 2,
    DELETED = 3
}

export const ProjectVersionStatusLabelMap = {
    [ProjectVersionStatus.RELEASED] : "Released",
    [ProjectVersionStatus.SCHEDULED] : "Scheduled",
    [ProjectVersionStatus.DELETED] : "Deleted"
}


export interface ProjectVersionSimple {
    majorVersion: number;
    minorVersion: number;
    patchVersion: number;
    versionLabel: string;
    releaseDate: Date;
}

export class ProjectVersionDeserializer {

    public static fromProjectVersionSimple = (value: any) : ProjectVersionSimple => (
        {
            majorVersion: value.majorVersion,
            minorVersion: value.minorVersion,
            patchVersion: value.patchVersion,
            versionLabel: value.versionLabel,
            releaseDate: value.releaseDate
        }
    );

    public static fromProjectVersionList = (value: any) : ProjectVersion[] => (
        value.map( 
            (val: any) => ProjectVersionDeserializer.fromProjectVersion(val)
        )
    );

    public static fromProjectVersion = (value: any) : ProjectVersion => (
        {
            majorVersion: value.majorVersion,
            minorVersion: value.minorVersion,
            patchVersion: value.patchVersion,
            versionLabel: value.versionLabel,
            statusId: value.statusId,
            note: value.note,
            releaseDate: value.releaseDate 
        }
    );
}