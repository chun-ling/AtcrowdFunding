package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Menu;

import java.util.List;

/**
 * @author YUChangcan
 * @date 2022/12/25 - 11:32
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}
