package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FolderService {

    private final FolderRepository folderRepository;

    @Transactional
    public void addFolders(List<String> folderNames, User user) {

        // 입력으로 들어온 폴더 이름을 기준으로, 회원이 이미 생성한 폴더들을 조회합니다.
        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);
        Map<String, Folder> existFolderMap = createExistFolderMapBy(existFolderList);

        List<Folder> folderList = new ArrayList<>();
        folderNames.forEach(name -> {
            // 이미 생성한 폴더가 아닌 경우만 폴더 생성
            if (!existFolderMap.containsKey(name)) {
                Folder saveFolder = new Folder(name, user);
                folderList.add(saveFolder);
            } else {
                throw new IllegalArgumentException("중복된 폴더명을 제거해 주세요! 폴더명: " + name);
            }
        });

        folderRepository.saveAll(folderList);
    }

    // 로그인한 회원이 등록된 모든 폴더 조회
    @Transactional(readOnly = true)
    public List<FolderResponseDto> getFolders(User user) {
        return folderRepository.findAllByUser(user).stream().map(FolderResponseDto::new).toList();
    }

    private static Map<String, Folder> createExistFolderMapBy(List<Folder> existFolderList) {
        return existFolderList.stream().collect(Collectors.toMap(Folder::getName, f -> f));
    }
}
