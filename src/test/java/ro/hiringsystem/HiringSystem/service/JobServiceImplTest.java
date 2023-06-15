package ro.hiringsystem.HiringSystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ro.hiringsystem.mapper.JobMapper;
import ro.hiringsystem.model.dto.JobDto;
import ro.hiringsystem.model.entity.Job;
import ro.hiringsystem.repository.JobRepository;
import ro.hiringsystem.service.impl.JobServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Job Service Tests")
class JobServiceImplTest {

    private JobServiceImpl jobService;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobMapper jobMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jobService = new JobServiceImpl(jobRepository, jobMapper);
    }

    @Test
    @DisplayName("Test getById with existing id should return JobDto")
    void testGetById_ExistingId_ReturnsJobDto() {
        UUID id = UUID.randomUUID();
        Job job = new Job();
        JobDto expectedJobDto = new JobDto();
        when(jobRepository.findById(id)).thenReturn(Optional.of(job));
        when(jobMapper.toDto(job)).thenReturn(expectedJobDto);


        JobDto result = jobService.getById(id);


        assertNotNull(result);
        assertEquals(expectedJobDto, result);
        verify(jobRepository).findById(id);
        verify(jobMapper).toDto(job);
    }

    @Test
    @DisplayName("Test getById with non-existing id should throw RuntimeException")
    void testGetById_NonExistingId_ThrowsRuntimeException() {
        UUID id = UUID.randomUUID();
        when(jobRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class, () -> jobService.getById(id));
        verify(jobRepository).findById(id);
    }


    @Test
    @DisplayName("Test getAllFromMap should return JobDto map")
    void testGetAllFromMap_ReturnsJobDtoMap() {
        List<Job> jobList = new ArrayList<>();
        Job job1 = new Job();
        job1.setId(UUID.randomUUID());
        jobList.add(job1);
        Job job2 = new Job();
        job2.setId(UUID.randomUUID());
        jobList.add(job2);

        JobDto jobDto1 = new JobDto();
        jobDto1.setId(job1.getId());
        JobDto jobDto2 = new JobDto();
        jobDto2.setId(job2.getId());

        when(jobRepository.findAll()).thenReturn(jobList);
        when(jobMapper.toDto(job1)).thenReturn(jobDto1);
        when(jobMapper.toDto(job2)).thenReturn(jobDto2);


        Map<UUID, JobDto> result = jobService.getAllFromMap();


        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey(jobDto1.getId()));
        assertTrue(result.containsKey(jobDto2.getId()));
        assertEquals(jobDto1, result.get(jobDto1.getId()));
        assertEquals(jobDto2, result.get(jobDto2.getId()));
        verify(jobRepository).findAll();
        verify(jobMapper, times(2)).toDto(any(Job.class));
    }

    @Test
    @DisplayName("Test add should save JobDto")
    void testAdd_SavesJobDto() {
        JobDto jobDto = new JobDto();
        Job job = new Job();

        when(jobMapper.toEntity(jobDto)).thenReturn(job);


        jobService.add(jobDto);


        verify(jobRepository).save(job);
        verify(jobMapper).toEntity(jobDto);
    }

    @Test
    @DisplayName("Test removeElementById with existing id should delete Job")
    void testRemoveElementById_ExistingId_DeletesJob() {
        UUID id = UUID.randomUUID();
        Job job = new Job();

        when(jobRepository.findById(id)).thenReturn(Optional.of(job));


        jobService.removeElementById(id);


        verify(jobRepository).findById(id);
        verify(jobRepository).delete(job);
    }

    @Test
    @DisplayName("Test removeElementById with non-existing id should throw RuntimeException")
    void testRemoveElementById_NonExistingId_ThrowsRuntimeException() {
        UUID id = UUID.randomUUID();
        when(jobRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class, () -> jobService.removeElementById(id));
        verify(jobRepository).findById(id);
    }

    @Test
    @DisplayName("Test saveElement should save JobDto")
    void testSaveElement_SavesJobDto() {
        JobDto jobDto = new JobDto();
        Job job = new Job();

        when(jobMapper.toEntity(jobDto)).thenReturn(job);


        jobService.saveElement(jobDto);


        verify(jobRepository).save(job);
        verify(jobMapper).toEntity(jobDto);
    }

    @Test
    @DisplayName("Test listToMap should convert JobDto list to map")
    void testListToMap_ConvertsJobDtoListToMap() {
        List<JobDto> jobDtoList = new ArrayList<>();
        JobDto jobDto1 = new JobDto();
        jobDto1.setId(UUID.randomUUID());
        jobDtoList.add(jobDto1);
        JobDto jobDto2 = new JobDto();
        jobDto2.setId(UUID.randomUUID());
        jobDtoList.add(jobDto2);


        Map<UUID, JobDto> result = jobService.listToMap(jobDtoList);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey(jobDto1.getId()));
        assertTrue(result.containsKey(jobDto2.getId()));
        assertEquals(jobDto1, result.get(jobDto1.getId()));
        assertEquals(jobDto2, result.get(jobDto2.getId()));
    }

    @Test
    @DisplayName("Test createEdit should save JobDto and return it")
    void testCreateEdit_SavesJobDtoAndReturnsIt() {
        JobDto jobDto = new JobDto();
        Job job = new Job();

        when(jobMapper.toEntity(jobDto)).thenReturn(job);


        JobDto result = jobService.createEdit(jobDto);


        assertNotNull(result);
        assertEquals(jobDto, result);
        verify(jobRepository).save(job);
        verify(jobMapper).toEntity(jobDto);
    }

    @Test
    @DisplayName("Test getAll should return all JobDtos")
    void testGetAll_ReturnsAllJobDtos() {
        List<Job> jobList = new ArrayList<>();
        Job job1 = new Job();
        job1.setId(UUID.randomUUID());
        jobList.add(job1);
        Job job2 = new Job();
        job2.setId(UUID.randomUUID());
        jobList.add(job2);

        JobDto jobDto1 = new JobDto();
        jobDto1.setId(job1.getId());
        JobDto jobDto2 = new JobDto();
        jobDto2.setId(job2.getId());

        when(jobRepository.findAll()).thenReturn(jobList);
        when(jobMapper.toDto(job1)).thenReturn(jobDto1);
        when(jobMapper.toDto(job2)).thenReturn(jobDto2);


        List<JobDto> result = jobService.getAll();


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(jobDto1, result.get(0));
        assertEquals(jobDto2, result.get(1));
        verify(jobRepository).findAll();
        verify(jobMapper, times(2)).toDto(any(Job.class));
    }

    @Test
    @DisplayName("Test getAll with page and size should return paginated JobDtos")
    void testGetAll_WithPageAndSize_ReturnsJobDtosWithPagination() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Job> jobList = new ArrayList<>();
        Job job1 = new Job();
        job1.setId(UUID.randomUUID());
        jobList.add(job1);
        Job job2 = new Job();
        job2.setId(UUID.randomUUID());
        jobList.add(job2);

        JobDto jobDto1 = new JobDto();
        jobDto1.setId(job1.getId());
        JobDto jobDto2 = new JobDto();
        jobDto2.setId(job2.getId());

        Page<Job> jobPage = new PageImpl<>(jobList, pageRequest, jobList.size());

        when(jobRepository.findAll(pageRequest)).thenReturn(jobPage);
        when(jobMapper.toDto(job1)).thenReturn(jobDto1);
        when(jobMapper.toDto(job2)).thenReturn(jobDto2);


        List<JobDto> result = jobService.getAll(page, size);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(jobDto1, result.get(0));
        assertEquals(jobDto2, result.get(1));
        verify(jobRepository).findAll(pageRequest);
        verify(jobMapper, times(2)).toDto(any(Job.class));
    }
}
